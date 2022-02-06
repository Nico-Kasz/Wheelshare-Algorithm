import * as React from 'react';
import { useState } from 'react';
import ReactMapGL, { Marker } from 'react-map-gl';
import 'bootstrap/dist/css/bootstrap.css';

import pin from "./icons/pin.png";

const api = 'https://api.mapbox.com/geocoding/v5/mapbox.places/';
const token = 'pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA';



async function getForwardGeocoding(search_text) {
    const response = await fetch(api + search_text + '.json?access_token=' + token);
    const data = await response.json();
    makeMarker(data.features[0].center[0], data.features[0].center[1]);
    console.log(data.features[0].center);
    return data;
}


function makeMarker(long, lat) {
    return (            // Need to fix these variables   VV anchor and VV scale arent applying properties in map rendering
        <Marker longitude={long} latitude={lat} anchor="bottom" Scale=".2">
            <img src={pin} alt="pin" />
        </Marker>
    );
}


export default function Map() {
    const [viewport, setViewport] = useState({
        width: '100vw',
        height: '100vh',
        latitude: 39.50882818527073,
        longitude: -84.73455522976074,
        zoom: 14
    });

    return (
        <div>
                    <ReactMapGL
                        {...viewport}
                        mapboxApiAccessToken={token}
                    mapStyle="mapbox://styles/nicokasz/ckz23hv99001w14qmii9m7ac4"
                    onViewportChange={(viewport) => { setViewport(viewport); }}
                    >
            
                    {makeMarker(-85, 40)}
            
            </ReactMapGL>
        </div>
        );
} 