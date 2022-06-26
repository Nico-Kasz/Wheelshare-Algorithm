import * as React from "react";
import { useState } from "react";
import ReactMapGL, {
  GeolocateControl,
  NavigationControl,
  Marker,
  Source,
  Layer,
} from "react-map-gl";
import mapboxgl from "mapbox-gl";
//import { heatmapLayer } from "./heatmap";
import { routeLine } from "./routeLine";
import Pin from "./Pin";
import "../Assets/CSS/Map.css";
import { API, TOKEN, MapStyle } from "./constants";

// The following is required to stop "npm build" from transpiling mapbox code.
// notice the exclamation point in the import.
// @ts-ignore
// eslint-disable-next-line import/no-webpack-loader-syntax, import/no-unresolved
mapboxgl.workerClass = require("worker-loader!mapbox-gl/dist/mapbox-gl-csp-worker").default;

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
const updateMap = () => {}
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
        onDragEnd={(ev) => {
          UpdateCurrentMarker(marker.name, ev.lngLat[0], ev.lngLat[1]);
          updateMap();
        }
        }
      >
        <Pin />
      </Marker>
    );
  });
};

const DisplayRoute = () => {
  // For testing purposes, doesn't display if only one Marker is used - cuases second location to be at 0,0
  return true ? null : (
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
  const D = () => {
     const args = {
            method: "POST",
            mode: "no-cors",
            headers: { "Content-Type": "raw" },
            body: "241 Eisenhower Way",
          };

          fetch(
            "http://testing.mypath.routemypath.com:8000/api/v1/address/",
            args
          )
            .then((response) => response.json())
            .then((data) => console.log(data));
  }

  return (
    <>
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
        onMouseDown= {() => setViewport(viewport)}
        // Set additional marker - on double click
        onDblClick={(ev) => {
          Markers[0] = { name: Markers[0].name };
          Markers[1] = { name: Markers[1].name };
          console.log(ReactMapGL)
        }}
        //ref={(m) => mapRef = m }
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
    </>
  );
}

export { setAddress, changeOverlay };
