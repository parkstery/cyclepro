/**
 * SourceId: FREE-ROUTE-001
 * Purpose: OSRM profile 분기/스냅/재요청 기준 로직 참조
 * AdoptedIn: Pro data routing module
 * Status: reference-only
 * DoNotCopyPaste: true
 * UseAs: behavior reference and parity tests
 * LastSynced: 2026-04-14
 * ReferenceLinks:
 * - SourceId: FREE-ROUTE-001
 * - ProL1: c:/20.HDev/cycle_pro/docs/RTW_PRO_L1_00_BASELINE_CORE.md
 * - ProL2: c:/20.HDev/cycle_pro/docs/RTW_PRO_L2_00_03_OSRM_ROUTING.md
 */

const SNAP_RADIUS_M = 50;
const OSM_DE_BASE = 'https://routing.openstreetmap.de';

export function getRouteBase(profile: string): string {
  const p = String(profile || 'driving').toLowerCase();
  if (p === 'cycling' || p === 'bike') return `${OSM_DE_BASE}/routed-bike`;
  if (p === 'foot' || p === 'walk') return `${OSM_DE_BASE}/routed-foot`;
  return `${OSM_DE_BASE}/routed-car`;
}

export async function snapCoord(routeBase: string, coord: string): Promise<string> {
  const trimmed = coord.trim();
  if (!trimmed) return trimmed;
  const nearestUrl = `${routeBase}/nearest/v1/driving/${trimmed}?number=1&radiuses=${SNAP_RADIUS_M}`;
  try {
    const r = await fetch(nearestUrl);
    if (!r.ok) return trimmed;
    const data = (await r.json()) as { code?: string; waypoints?: Array<{ location?: [number, number] }> };
    if (data.code === 'Ok' && data.waypoints?.[0]?.location) {
      const [lng, lat] = data.waypoints[0].location;
      return `${lng},${lat}`;
    }
  } catch {
    // keep original coord
  }
  return trimmed;
}

export type OsrmRouteResponse = {
  code?: string;
  routes?: Array<{ geometry: string; distance: number; duration: number }>;
};

export async function fetchOsrmRouteJson(profile: string, coords: string): Promise<OsrmRouteResponse> {
  const coordList = coords.split(';').map((c) => c.trim()).filter(Boolean);
  if (coordList.length === 0) throw new Error('Empty coords');

  const base = getRouteBase(profile);
  const baseParams = 'overview=full&geometries=polyline&alternatives=false&steps=false';
  const fetchRoute = (coordsStr: string) => fetch(`${base}/route/v1/driving/${coordsStr}?${baseParams}`);

  const snappedList = await Promise.all(coordList.map((coord) => snapCoord(base, coord)));
  let r = await fetchRoute(snappedList.join(';'));
  let body = await r.text();

  // 400 발생 시 원본 좌표로 1회 재시도
  if (!r.ok && r.status === 400 && coordList.length >= 2) {
    r = await fetchRoute(coordList.join(';'));
    body = await r.text();
  }

  if (!r.ok) throw new Error(`OSRM ${r.status}: ${body.slice(0, 240)}`);
  return JSON.parse(body) as OsrmRouteResponse;
}
