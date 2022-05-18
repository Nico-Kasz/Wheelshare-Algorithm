const MIN_ZOOM_LEVEL = 10;
const MAX_ZOOM_LEVEL = 24;

const WEIGHT = [MIN_ZOOM_LEVEL, .004, MAX_ZOOM_LEVEL, .004];
const INTENSITY = [MIN_ZOOM_LEVEL, 5, MAX_ZOOM_LEVEL, 10];
const RADIUS = [MIN_ZOOM_LEVEL, 6, MAX_ZOOM_LEVEL, 14];
const OPACITY = [MIN_ZOOM_LEVEL - 1, 0, MIN_ZOOM_LEVEL, 1];

export const heatmapLayer = {
  id: 'heatmap',
  minzoom: MIN_ZOOM_LEVEL,
  maxzoom: MAX_ZOOM_LEVEL,
  type: 'heatmap',
  paint: {
    // Increase the heatmap weight based on frequency and property magnitude
    //'heatmap-weight': ['interpolate', ['linear'], ['get', 'mag'], 0, 0, 6, 1],
    'heatmap-weight': ['interpolate', ['linear'], ['zoom'], ...WEIGHT],


    // Increase the heatmap color weight weight by zoom level
    // heatmap-intensity is a multiplier on top of heatmap-weight
    'heatmap-intensity': ['interpolate', ['linear'], ['zoom'], ...INTENSITY],
    // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
    // Begin color ramp at 0-stop with a 0-transparancy color
    // to create a blur-like effect.
    'heatmap-color': [
      'interpolate',
      ['linear'],
      ['heatmap-density'],
      0,
      'rgba(255,0,0, .01)',
      0.005,
      'rgb(255,0,1)',
      0.3,
      'rgb(255,155,0)',
      0.6,
      'rgb(255,213,0)',
      0.85,
      'rgb(0,255,0)'
    ],
    // Adjust the heatmap radius by zoom level
    'heatmap-radius': ['interpolate', ['linear'], ['zoom'], ...RADIUS],
    // Transition from heatmap to circle layer by zoom level
    'heatmap-opacity': ['interpolate', ['linear'], ['zoom'], ...OPACITY]
  }
};