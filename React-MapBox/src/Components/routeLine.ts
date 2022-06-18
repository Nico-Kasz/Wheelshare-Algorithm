const MIN_ZOOM_LEVEL = 14;
const MAX_ZOOM_LEVEL = 22;
const ZOOM_INTERP = [MIN_ZOOM_LEVEL, 2, MAX_ZOOM_LEVEL, 10];

export const routeLine = {
  id: "route",
  type: "line",
  minzoom: MIN_ZOOM_LEVEL,
  "line-cap": "round",
  "line-blur": ["interpolate", ["linear"], ["zoom"], ...ZOOM_INTERP],
  filter: [
    "all",
    ["==", ["geometry-type"], "LineString"],
    [
      ">",
      [
        "+",
        ["to-number", ["get", "incline"]],
        ["to-number", ["get", "Incline"]],
        ["to-number", ["get", "slope"]],
      ],
      0,
    ],
  ],
  paint: {
    // Add if statement for stairs
    "line-color": [
      "interpolate",
      ["linear"],
      ["+", ["to-number", ["get", "slope"]], ["to-number", ["get", "incline"]]],
      0,
      "rgb(0,255,0)",
      1.0,
      "rgb(50,255,0)",
      3.0,
      "rgb(150,255,0)",
      5.0,
      "rgb(255,165,0)",
      8.0,
      "rgb(255,100,0)",
      15.0,
      "rgb(255,0,0)",
    ],
    "line-width": ["interpolate", ["linear"], ["zoom"], ...ZOOM_INTERP],
  },
}; 
