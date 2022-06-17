const MIN_ZOOM_LEVEL = 14;
const MAX_ZOOM_LEVEL = 18;
const ZOOM_INTERP = [MIN_ZOOM_LEVEL, 1, MAX_ZOOM_LEVEL, 2]


export const routeLine = {
    "id": "route", 
    "type": "line",
    "minzoom": MIN_ZOOM_LEVEL,
    "filter": [
      "all",
        [
          "==",
          [
            "geometry-type"
          ],
          "LineString"
        ],
        [
        "match",
        ["get", "foot"],
        ["permissive"],
        true,
        false
        ]
    ],
    paint: {   
      'line-color': [
        'interpolate',
        ['linear'],
        ['get', 'incline'],
        -1,
        'rgba(255,0,0, .0)',
        0.0,
        'rgba(255,0,0, 1)',
        1.0,
        'rgba(255,0,0, 1)',
        3.0,
        'rgb(255,0,1)',
        5.0,
        'rgb(255,155,1)',
        6.0,
        'rgb(255,213,1)',
        15.0,
        'rgb(0,255,1)'
      ],
      "line-width": [
        "interpolate",
        ["linear"],
        ["zoom"],
        ...ZOOM_INTERP
      ]
      }
}