import * as React from "react";
import { useState } from "react";
import ReactMapGL, { GeolocateControl, NavigationControl, Marker, Source, Layer } from "react-map-gl";
import { heatmapLayer } from "./heatmap";
import Pin from "./Pin";
import "../Assets/CSS/Map.css";

// Constants Imports
import { API, TOKEN, MapStyle } from "./constants";




// ================================= Variables =================================
let heatmapData = null;

// {name: _, address: _, longitude: _, latitude: _}
const Markers = [{ name: "StartMarker" }, { name: "EndMarker" }];





// ================================= Modifying Methods =================================
// TODO - On UI button click => retrieve new heatmap data
const changeHeatmap = (src) => {
  // src should be an api endpoint
	heatmapData = src;
	updateMap(); 
}


// Index is intended to be 0 or 1 for start/end locations
const setAddress = (index, address) => {
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
  if (Markers.length === 3) 
  	Markers.pop();
};


// TODO - rerender Map
const updateMap = () => {};


// Called by marker name to update new coordinates
const UpdateCurrentMarker = (name, longitude, latitude) => {
	const index = Markers.findIndex((m) => {
	  return m.name === name;
	});
   
	Markers[index].longitude = longitude;
	Markers[index].latitude = latitude;
   };
   



// ================================= Components =================================
const DisplayHeatmap = () => {
    return (
      heatmapData && (
        <Source type="geojson" data={heatmapData}>
          <Layer {...heatmapLayer} />
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
        onDragEnd={(ev) => UpdateCurrentMarker(marker.name, ev.lngLat[0], ev.lngLat[1])}
      >
        <Pin />
      </Marker>
    );
  });
};



export default function Map() {
  const [viewport, setViewport] = useState({
    width: '100vw',
    height: '100vh',
    hash: true,
    latitude: 39.50882818527073,
    longitude: -84.73455522976074,
    zoom: 14,
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
    doubleClickZoom: false
  }


  return (
    <div>
      <ReactMapGL
        {...viewport}
        {...settings}
        mapboxApiAccessToken={TOKEN}
        mapStyle={MapStyle}
        onViewportChange={(viewport) => {
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
        {DisplayHeatmap()}

        {DisplayMarkers()}

        <GeolocateControl
          className="GeoLocate"
          trackUserLocation={true}
          maxZoom={22}
        />

        <NavigationControl 
		className="NavControl" 
		showCompass={false} 
	   />

      </ReactMapGL>
    </div>
  );
}

export { setAddress, changeHeatmap};
