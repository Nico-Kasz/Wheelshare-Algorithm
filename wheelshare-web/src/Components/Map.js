import * as React from 'react';
import { useState } from 'react';
import ReactMapGL, { GeolocateControl, Marker, Source, Layer } from 'react-map-gl';
import {heatmapLayer} from './heatmap';
import Pin from './Pin';

// Constants Imports
import { API, TOKEN } from "./constants";




var heatmapOn = false;
const heatmapData =  require('../Assets/Geojsons/4.20.22 gps data.geojson'); // Temp data

// {name: _, address: _, longitude: _, latitude: _}
const Markers = [{name: "StartMarker"}, {name: "EndMarker"}];


const toggleHeatMap = () => {heatmapOn = !heatmapOn}

// Index is intended to be 0 or 1 for start/end locations 
const setAddress = (index, address)  => {
    // Call Geocoding here
    fetch(API + encodeURIComponent(address) + '.json?access_token=' + TOKEN)
                .then(resp => resp.json())
                .then(json => {
                    if (json.features[0] !== undefined)           // Just incase
                        // Set marker at index to JSON values
                        Markers[index] = {
                            name: Markers[index].name,
                            address: address, 
                            longitude: json.features[0].center[0], 
                            latitude: json.features[0].center[1]
                        }})
                .catch((error) => {
                    console.error('Error:', error);
                });
    updateMap(); 

    // Deletes Double click marker
    if (Markers.length === 3)
        Markers.pop();
}


// TODO - rerender Map
const updateMap = () => {}


const DisplayHeatmap = () => {
    if (heatmapOn)
        return (
            heatmapData && (
            <Source type="geojson" data={heatmapData}>
            <Layer {...heatmapLayer}/>
            </Source>)
        )
}

// Called by marker name to update new coordinates
const UpdateCurrentMarker = (name, longitude, latitude) => {
    const index = Markers.findIndex(m => {return m.name === name});

    Markers[index].longitude = longitude;
    Markers[index].latitude = latitude;
}



// Displays marker if it contains longitude and latitude coordinates
const DisplayMarkers = () => {
    return Markers.map(function (marker) {
        // Check if marker has coordinates
        if (!('longitude' in marker && 'latitude' in marker))
            return null;

        // Handle marker onDrag
        const markerOnDragEvent = (ev) => {
            UpdateCurrentMarker(marker.name, ev.lngLat[0], ev.lngLat[1]);
        }

        // Return the marker for display
        return(
            <Marker 
                className={ marker.name }
                longitude= {marker.longitude } 
                latitude={ marker.latitude } 
                anchor= 'bottom'
                key = {marker.name}        // TODO - These need to be unique 
                pitchAlignment= 'map'
                draggable={true}
                onDragEnd={ ev => markerOnDragEvent(ev)}>
                <Pin/> 
            </Marker>)
        }
    );

}   


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
            maxPitch: 30
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
                    mapboxApiAccessToken = {TOKEN}
                    mapStyle = "mapbox://styles/nicokasz/ckz23hv99001w14qmii9m7ac4"
                    onViewportChange = {(viewport) => { setViewport(viewport); }}
                    // Set additional marker - on double click
                    onDblClick = {(ev) => Markers[2] = {name: "Marked Location", longitude: ev.lngLat[0], latitude: ev.lngLat[1]}}
                >

                {DisplayHeatmap()}

                {DisplayMarkers()}
                    
                <GeolocateControl trackUserLocation={true} maxZoom={22}/>
                    
                </ReactMapGL>
            </div>
            );
}

export {setAddress, toggleHeatMap};