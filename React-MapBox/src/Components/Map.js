import * as React from "react";
import { useState } from "react";
import ReactMapGL, {
  GeolocateControl,
  NavigationControl,
  Marker,
  Source,
  Layer,
} from "react-map-gl";
//import { heatmapLayer } from "./heatmap";
import { routeLine } from "./routeLine";
import Pin from "./Pin";
import "../Assets/CSS/Map.css";
import { API, TOKEN, MapStyle } from "./constants";

// {name: _, address: _, longitude: _, latitude: _}
const Markers = [{ name: "StartMarker" }, { name: "EndMarker" }];
let overlayData = null;
let lineData = null;

const changeOverlay = (src) => {
  // src should be an api endpoint
  overlayData = src;
  updateMap();
};

// Index is intended to be 0 or 1 for start/end locations
const setAddress = (index, address) => {
  // Check input
  if (address === "" || address === null) {
    Markers[index] = {
      name: Markers[index].name,
    };
  }

  // Call Geocoding here
  fetch(API + encodeURIComponent(address) + ".json?access_token=" + TOKEN)
    .then((resp) => resp.json())
    .then((json) => {
      if (json.features[0] !== undefined)
        // Just incase
        // Set marker at index to JSON values
        Markers[index] = {
          name: Markers[index].name,
          address: address,
          longitude: json.features[0].center[0],
          latitude: json.features[0].center[1],
        };
        if ('longitude' in Markers[0] && 'longitude' in Markers[1])
          // This is where we call API for route data
          // Test data do display a line
          lineData = require("../Assets/Geojsons/route test data 1.geojson");
    })
    .catch((error) => {
      console.error("Error:", error);
    });

  // Rerender map
  updateMap();

  // Deletes Double click marker
  if (Markers.length === 3) Markers.pop();
};

// TODO - rerender Map - Maybe do useEffect in Map
const updateMap = () => {};

// Called by marker name to update new coordinates
const UpdateCurrentMarker = (name, longitude, latitude) => {
  const index = Markers.findIndex((m) => {
    return m.name === name;
  });

  Markers[index].longitude = longitude;
  Markers[index].latitude = latitude;
};

const DisplayOverlay = () => {
  return (
    overlayData && (
      <Source type="geojson" data={overlayData}>
        <Layer {...routeLine} />
      </Source>
    )
  );
};

// Displays marker if it contains longitude and latitude coordinates
const DisplayMarkers = () => {
  return Markers.map(function (marker) {
    // Check if marker has coordinates
    if (!("longitude" in marker && "latitude" in marker)) return null;

    // Return the marker for display
    return (
      <Marker
        className={marker.name}
        longitude={marker.longitude}
        latitude={marker.latitude}
        anchor="bottom"
        key={marker.name}
        pitchAlignment="map"
        draggable={true}
        onDragEnd={(ev) =>
          UpdateCurrentMarker(marker.name, ev.lngLat[0], ev.lngLat[1])
        }
      >
        <Pin />
      </Marker>
    );
  });
};



const DisplayRoute = () => {
  // For testing purposes, doesn't display if only one Marker is used - cuases second location to be at 0,0
  if (!("latitude" in Markers[0] && "latitude" in Markers[1])) {
    return null;
  }

  return (
    lineData && (
      <Source type="geojson" data={lineData}>
        <Layer {...routeLine} />
      </Source>
    )
  );
};

export default function Map() {
  const [viewport, setViewport] = useState({
    width: "100vw",
    height: "100vh",
    hash: true,
    latitude: 39.50882818527073,
    longitude: -84.73455522976074,
    zoom: 17,
    maxZoom: 20,
    minPitch: 0,
    maxPitch: 30,
  });

  const settings = {
    dragPan: true,
    dragRotate: false,
    scrollZoom: true,
    touchZoom: false,
    touchRotate: false,
    keyboard: true,
    doubleClickZoom: false,
  };

  // TODO - Add use effect to update?

  return (
    <div>
      <ReactMapGL
        {...viewport}
        {...settings}
        mapboxApiAccessToken={TOKEN}
        mapStyle={MapStyle}
        onViewportChange={(viewport) => {
          viewport.height = window.innerHeight;
          viewport.width = window.innerWidth;
          setViewport(viewport);
        }}
        // Set additional marker - on double click
        onDblClick={(ev) =>
          (Markers[2] = {
            name: "Marked Location",
            address: "none",
            longitude: ev.lngLat[0],
            latitude: ev.lngLat[1],
          })
        }
      >
        {DisplayOverlay()}
        {DisplayMarkers()}
        {DisplayRoute()}

        <GeolocateControl
          className="GeoLocate"
          trackUserLocation={true}
          maxZoom={22}
        />
        <NavigationControl className="NavControl" showCompass={false} />
      </ReactMapGL>
    </div>
  );
}

export { setAddress, changeOverlay };
