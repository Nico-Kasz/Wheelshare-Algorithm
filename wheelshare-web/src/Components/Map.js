import * as React from 'react';
import { useState} from 'react';
import ReactMapGL, { GeolocateControl, Marker, Source, Layer } from 'react-map-gl';
import {heatmapLayer} from './heatmap';
import Pin from './Pin.tsx';

// Constants Imports
import { API, TOKEN } from "./constants";




var heatmapOn = false;
const heatmapData =  require('../Assets/Geojsons/4.20.22 gps data.geojson');

// {name: _, address: _, longitude: _, latitude: _}
const Markers = [{name: "StartMarker", address: "Billy ave"}, {name: "EndMarker"}, {latitude: 39.50882818527073, longitude: -84.73455522976074, name: "TestMarker"}];

const setStartAddress   = (address) => {Markers[0].address = address;}
const setEndAddress     = (address) => {Markers[1].address = address;}


const DisplayHeatmap = () => {
    if (heatmapOn)
        return (
            heatmapData && (
            <Source type="geojson" data={heatmapData}>
            <Layer {...heatmapLayer}/>
            </Source>)
        )
}

// Might have to have for let i, then use map to display all 
const DisplayMarkers = () => {
    return Markers.map(function (marker) {
        console.log(marker);
        if (!('latitude' in marker && 'longitude' in marker)) {
            if ('address' in marker) {
                // Forward Geocode - TODO - Async Wait => set marker
                //marker = GetLocationByAddress(marker.address).then();
                marker = GetLocationByAddress(marker.address, marker.name);
            } else
                // Blank marker - nothing to display
                ;
        } 
        //Since GeoCoding doesnt work as of now, the marker data is logged
        console.log(marker);


        // Marker on drag event => moves to location it is dropped at - TODO - throws exception
        const markerOnDragEvent = (ev) => {
            marker = {name: marker.name, address: marker.address, longitude: ev.lngLat[0], latitude: ev.lngLat[1]};
            //marker.longitude = ev.lngLat[0];
            //marker.latitude =  ev.lngLat[1];
            //console.log(ev);
        }

        return(
            <Marker 
                className={'name' in marker ? marker.name : "Marker"}
                longitude={'longitude' in marker? marker.longitude : 0}  // The "in" statements are placeholders
                latitude={'latitude' in marker? marker.latitude : 0} 
                anchor="bottom"
                key = {marker.name}         // Make this unique TODO
                pitchAlignment= 'map'
                draggable={true}
                onDragEnd={ ev => markerOnDragEvent(ev)}>
                <Pin/> 
            </Marker>)
        }
    );

}

const GetLocationByAddress = (address, name) => {
    let marker = 
        fetch(API + encodeURIComponent(address) + '.json?access_token=' + TOKEN)
                .then(resp => resp.json())
                .then(json => {
                    console.log(json);
                    if (json.features[0] !== undefined)           // Just incase
                        return {name: name===undefined? "Marker" : name, address: address, longitude: json.features[0].center[0], latitude: json.features[0].center[1]};
                })
    return marker;
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
        

        return (
            <div>
                
                <ReactMapGL
                    {...viewport}
                    mapboxApiAccessToken={TOKEN}
                    mapStyle="mapbox://styles/nicokasz/ckz23hv99001w14qmii9m7ac4"
                    onViewportChange={(viewport) => { setViewport(viewport); }}
                >

                {DisplayHeatmap()}

                {DisplayMarkers()}
                    
                <GeolocateControl trackUserLocation={true} maxZoom={22}/>
                    
                </ReactMapGL>
            </div>
            );
}