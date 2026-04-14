/**
 * SourceId: FREE-GEO-001
 * Purpose: 경로/거리/heading 계산 기준 로직 참조
 * AdoptedIn: Pro domain geo module
 * Status: reference-only
 * DoNotCopyPaste: true
 * UseAs: behavior reference and parity tests
 * LastSynced: 2026-04-14
 * ReferenceLinks:
 * - SourceId: FREE-GEO-001
 * - ProL1: c:/20.HDev/cycle_pro/docs/RTW_PRO_L1_00_BASELINE_CORE.md
 * - ProL2: c:/20.HDev/cycle_pro/docs/RTW_PRO_L2_00_05_GEO_DOMAIN_RULES.md
 */

/** Encoded polyline -> [[lat, lng], ...] */
export function decodePath(encoded: string): [number, number][] {
  const points: [number, number][] = [];
  let index = 0;
  let lat = 0;
  let lng = 0;
  while (index < encoded.length) {
    let b: number;
    let shift = 0;
    let result = 0;
    do {
      b = encoded.charCodeAt(index++) - 63;
      result |= (b & 0x1f) << shift;
      shift += 5;
    } while (b >= 0x20);
    const dlat = (result & 1) !== 0 ? ~(result >> 1) : result >> 1;
    lat += dlat;

    shift = 0;
    result = 0;
    do {
      b = encoded.charCodeAt(index++) - 63;
      result |= (b & 0x1f) << shift;
      shift += 5;
    } while (b >= 0x20);
    const dlng = (result & 1) !== 0 ? ~(result >> 1) : result >> 1;
    lng += dlng;

    points.push([lat / 1e5, lng / 1e5]);
  }
  return points;
}

type LatLngLike = { lat?: number; lng?: number } | { lat: () => number; lng: () => number };

function getLatLng(p: LatLngLike): { lat: number; lng: number } {
  const lat = typeof (p as { lat?: () => number }).lat === 'function' ? (p as { lat: () => number }).lat() : (p as { lat?: number }).lat;
  const lng = typeof (p as { lng?: () => number }).lng === 'function' ? (p as { lng: () => number }).lng() : (p as { lng?: number }).lng;
  return { lat: lat ?? 0, lng: lng ?? 0 };
}

/** Haversine distance meters */
export function computeDistanceBetween(a: LatLngLike, b: LatLngLike): number {
  const { lat: lat1, lng: lng1 } = getLatLng(a);
  const { lat: lat2, lng: lng2 } = getLatLng(b);
  const R = 6371000;
  const dLat = ((lat2 - lat1) * Math.PI) / 180;
  const dLon = ((lng2 - lng1) * Math.PI) / 180;
  const x =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos((lat1 * Math.PI) / 180) * Math.cos((lat2 * Math.PI) / 180) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
  const c = 2 * Math.atan2(Math.sqrt(x), Math.sqrt(1 - x));
  return R * c;
}

/** Heading from->to (0..360) */
export function computeHeading(from: LatLngLike, to: LatLngLike): number {
  const { lat: lat1, lng: lng1 } = getLatLng(from);
  const { lat: lat2, lng: lng2 } = getLatLng(to);
  const dLon = ((lng2 - lng1) * Math.PI) / 180;
  const y = Math.sin(dLon) * Math.cos((lat2 * Math.PI) / 180);
  const x =
    Math.cos((lat1 * Math.PI) / 180) * Math.sin((lat2 * Math.PI) / 180) -
    Math.sin((lat1 * Math.PI) / 180) * Math.cos((lat2 * Math.PI) / 180) * Math.cos(dLon);
  const brng = (Math.atan2(y, x) * 180) / Math.PI;
  return (brng + 360) % 360;
}

/** Offset point by distance/heading */
export function computeOffset(from: LatLngLike, distanceM: number, headingDeg: number): { lat: number; lng: number } {
  const { lat: lat1, lng: lng1 } = getLatLng(from);
  const R = 6371000;
  const d = distanceM / R;
  const brng = (headingDeg * Math.PI) / 180;
  const lat2 = Math.asin(
    Math.sin((lat1 * Math.PI) / 180) * Math.cos(d) +
      Math.cos((lat1 * Math.PI) / 180) * Math.sin(d) * Math.cos(brng)
  );
  const lng2 =
    (lng1 * Math.PI) / 180 +
    Math.atan2(
      Math.sin(brng) * Math.sin(d) * Math.cos((lat1 * Math.PI) / 180),
      Math.cos(d) - Math.sin((lat1 * Math.PI) / 180) * Math.sin(lat2)
    );
  return { lat: (lat2 * 180) / Math.PI, lng: (lng2 * 180) / Math.PI };
}
