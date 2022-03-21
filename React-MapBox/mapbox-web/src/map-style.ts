const MAX_ZOOM_LEVEL = 14;

export const heatmapLayer = {
  id: 'heatmap',
  maxzoom: 20,
  type: 'heatmap',
  paint: {
    // Increase the heatmap weight based on frequency and property magnitude
    'heatmap-weight': ['interpolate', ['linear'], ['get', 'mag'], 0, 0, 6, 1],
    // Increase the heatmap color weight weight by zoom level
    // heatmap-intensity is a multiplier on top of heatmap-weight
    'heatmap-intensity': ['interpolate', ['linear'], ['zoom'], 0, 1, MAX_ZOOM_LEVEL, 3],
    // Color ramp for heatmap.  Domain is 0 (low) to 1 (high).
    // Begin color ramp at 0-stop with a 0-transparancy color
    // to create a blur-like effect.
    'heatmap-color': [
      'interpolate',
      ['linear'],
      ['heatmap-density'],
      0,
      'rgba(255,0,0, 0)',
      0.05,
      'rgb(255,0,0)',
      0.3,
      'rgb(255,155,0)',
      0.6,
      'rgb(255,213,0)',
      0.85,
      'rgb(255,255,0)',
      0.9,
      'rgb(104,255,0)',
      0.95,
      'rgb(0,255,0)'
    ],
    // Adjust the heatmap radius by zoom level
    'heatmap-radius': ['interpolate', ['linear'], ['zoom'], 0, 2, 8, 10],
    // Transition from heatmap to circle layer by zoom level
    'heatmap-opacity': ['interpolate', ['linear'], ['zoom'], 7, 1, 9, 3]
  }
};