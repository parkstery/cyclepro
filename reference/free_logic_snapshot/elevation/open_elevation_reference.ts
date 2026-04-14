/**
 * SourceId: FREE-ELEV-001
 * Purpose: 표고 샘플링/캐시/조회 기준 로직 참조
 * AdoptedIn: Pro data elevation module
 * Status: reference-only
 * DoNotCopyPaste: true
 * UseAs: behavior reference and parity tests
 * LastSynced: 2026-04-14
 * ReferenceLinks:
 * - SourceId: FREE-ELEV-001
 * - ProL1: c:/20.HDev/cycle_pro/docs/RTW_PRO_L1_00_BASELINE_CORE.md
 * - ProL2: c:/20.HDev/cycle_pro/docs/RTW_PRO_L2_00_04_ELEVATION.md
 */

export interface OpenElevationResultItem {
  latitude: number;
  longitude: number;
  elevation: number;
}

export interface OpenElevationResponse {
  results: OpenElevationResultItem[];
}

type LatLngLike = { lat?: number | (() => number); lng?: number | (() => number) };

function getLatLng(p: LatLngLike): { lat: number; lng: number } {
  const lat = typeof p.lat === 'function' ? p.lat() : (p.lat as number) ?? 0;
  const lng = typeof p.lng === 'function' ? p.lng() : (p.lng as number) ?? 0;
  return { lat, lng };
}

/** path를 따라 samples개 지점 샘플링 (인덱스 균등) */
export function samplePath(path: LatLngLike[], samples: number): Array<{ latitude: number; longitude: number }> {
  if (path.length === 0) return [];
  if (path.length === 1) {
    const { lat, lng } = getLatLng(path[0]);
    return Array(samples).fill(null).map(() => ({ latitude: lat, longitude: lng }));
  }
  const locations: Array<{ latitude: number; longitude: number }> = [];
  for (let i = 0; i < samples; i++) {
    const t = samples === 1 ? 0 : i / (samples - 1);
    const idx = Math.min(Math.floor(t * (path.length - 1)), path.length - 1);
    const { lat, lng } = getLatLng(path[idx]);
    locations.push({ latitude: lat, longitude: lng });
  }
  return locations;
}

/** 경로 문자열화 (캐시 키용, 소수점 5자리) */
export function pathCacheKey(path: LatLngLike[], samples: number): string {
  const pts = samplePath(path, samples);
  return pts.map((p) => `${p.latitude.toFixed(5)},${p.longitude.toFixed(5)}`).join('|');
}

const elevationCache = new Map<string, OpenElevationResultItem[]>();

export async function getElevationAlongPath(
  path: LatLngLike[],
  samples: number = 100,
  endpoint: string = 'https://api.open-elevation.com/api/v1/lookup'
): Promise<OpenElevationResponse> {
  if (path.length === 0) return { results: [] };

  const key = pathCacheKey(path, samples);
  const cached = elevationCache.get(key);
  if (cached) return { results: cached };

  const locations = samplePath(path, samples);
  const res = await fetch(endpoint, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ locations }),
  });
  if (!res.ok) throw new Error(`Elevation ${res.status}`);

  const data = (await res.json()) as OpenElevationResponse;
  if (!data.results || !Array.isArray(data.results)) throw new Error('Elevation invalid response');

  elevationCache.set(key, data.results);
  return data;
}
