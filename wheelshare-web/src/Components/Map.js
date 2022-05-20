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

const setAddress        = (index, address)  => {Markers[index].address = address; updateMap()}
const toggleHeatMap     = ()                => {heatmapOn = !heatmapOn}
const updateMap         = ()                => {}


const DisplayHeatmap = () => {
    if (heatmapOn)
        return (
            heatmapData && (
            <Source type="geojson" data={heatmapData}>
            <Layer {...heatmapLayer}/>
            </Source>)
        )
}



// TODO => Move Update outside of Display to reduce lag
const DisplayMarkers = () => {
    return Markers.map(function (marker) {

        // Update marker positions 
        if (!('latitude' in marker && 'longitude' in marker)) {
            if ('address' in marker) {
                // Forward Geocode - TODO - Async Wait => set marker 
                // - can be combined with GetLocationByAddress for simplicity
                //marker = GetLocationByAddress(marker.address, marker.name);
            } else
                // Blank marker - nothing to update
                return null;
        } 

        // TODO - Marker on drag event => moves to location it is dropped at - Marker does not set
        const markerOnDragEvent = (ev) => {
            // Something like this V V 
            // UpdateMarker(marker.name, ev.lngLat[0], ev.lngLat[1]);
            marker= {name: marker.name, address: marker.address, longitude: ev.lngLat[0], latitude: ev.lngLat[1]};
        }

        // Return the marker for display
        return(
            <Marker 
                className={'name' in marker ? marker.name : "Marker"}
                longitude={'longitude' in marker? marker.longitude : 0}  // in statements are placeholders unitl Geocoding is done
                latitude={'latitude' in marker? marker.latitude : 0} 
                anchor= 'bottom'
                key = {marker.name}        // These need to be unique 
                pitchAlignment= 'map'
                draggable={true}
                onDragEnd={ ev => markerOnDragEvent(ev)}>
                <Pin/> 
            </Marker>)
        }
    );

}



const GetLocationByAddress = (address, name) => {
    return fetch(API + encodeURIComponent(address) + '.json?access_token=' + TOKEN)
                .then(resp => resp.json())
                .then(json => {
                    console.log(json);
                    if (json.features[0] !== undefined)           // Just incase
                        return {
                            name: name === undefined? "Marker" : name, 
                            address: address, 
                            longitude: json.features[0].center[0], 
                            latitude: json.features[0].center[1]
                        }
                });
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