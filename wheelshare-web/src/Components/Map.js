import * as React from 'react';
import { useState, useEffect} from 'react';
import ReactMapGL, { GeolocateControl, Marker, Source, Layer } from 'react-map-gl';
import {heatmapLayer} from './heatmap.ts';



// Constants and Image Imports
import { API, TOKEN } from "./constants.js";
import pin from "../Assets/Images/pin.png";




const Pin = () => {
    return (
        <img src={pin} alt="pin" draggable={false} height={'60px'} width={'40px'} style={{position: 'fixed', bottom: 0, right: -20 }}/>
    )
}



const DisplayMarker = (props) => {
    const [coords, setCoords] = useState({longitude: 0, latitude: 0}); 

    useEffect(() => {
        if (!(props.latitude == null || props.longitude == null)) {
            setCoords({longitude: props.longitude, latitude: props.latitude});
        }
        else {
            fetch(API + encodeURIComponent(props.address) + '.json?access_token=' + TOKEN)
            .then(resp => resp.json())
            .then(json => {
                if (json.features[0] !== undefined)           // Just incase
                    setCoords({longitude: json.features[0].center[0], latitude: json.features[0].center[1]});
            })
        }
    }, [props.address, props.longitude, props.latitude]);

    const markerOnDragEvent = (ev) => {
        setCoords({longitude: ev.lngLat[0], latitude: ev.lngLat[1]})
    }
    
    return (
        <Marker 
            longitude={coords.longitude} 
            latitude={coords.latitude} 
            anchor="bottom"
            pitchAlignment= 'map'
            draggable={true} 
            onDragEnd={ ev => markerOnDragEvent(ev)}
            style = {{display : props.address == null? "none" : "block" }} >
            <Pin />
        </Marker>
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
        
        const data =  require('../Assets/Geojsons/4.20.22 gps data.geojson');

        return (
            <div>
                
                <ReactMapGL
                    {...viewport}
                    mapboxApiAccessToken={TOKEN}
                    mapStyle="mapbox://styles/nicokasz/ckz23hv99001w14qmii9m7ac4"
                    onViewportChange={(viewport) => { setViewport(viewport); }}
                >
                    {data && (
                        <Source type="geojson" data={data}>
                            <Layer {...heatmapLayer}/>
                        </Source>
                    )}   

                    <div id="Markers">
                        <DisplayMarker address={"placeholder"}/>
                        <DisplayMarker address={"placeholder"}/>
                    </div>
                    
                    {<GeolocateControl trackUserLocation={true} maxZoom={22}/>}
                    
                </ReactMapGL>
            </div>
            );
}