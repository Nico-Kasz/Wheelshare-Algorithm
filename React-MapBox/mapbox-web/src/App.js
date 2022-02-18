import * as React from 'react';
import { useState, useEffect } from 'react';
import ReactMapGL, { Marker } from 'react-map-gl';
import 'bootstrap/dist/css/bootstrap.css';

import pin from "./icons/pin.png";

const api = 'https://api.mapbox.com/geocoding/v5/mapbox.places/';
const token = 'pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA';



const DisplayMarker = (props) => {
    const [coords, setCoords] = useState({ "lat": -0, "long": -0 });

    function myOnDragEnd(ev) {
        setCoords({ long: ev.lngLat[0], lat: ev.lngLat[1] });
    }

    useEffect(() => {
        getForwardGeocoding('Armstrong Student Center').then((data) => {
            setCoords({ "long": data.features[0].center[0], "lat": data.features[0].center[1] });
        });
    }, []);

    return (
        <>
            <Marker longitude={coords.long} latitude={coords.lat} anchor="bottom" draggable={true} onDragEnd={ myOnDragEnd }>
                <img src={pin} alt="pin" draggable={false} style={{ height: '60px', width: '40px'}/>
            </Marker>
        </>
    );
};


const getForwardGeocoding = async (search_text) => {
    const response = await fetch(api + search_text + '.json?access_token=' + token);
    const data = await response.json();
    return data;
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
                <DisplayMarker/>
            </ReactMapGL>
        </div>
        );
}