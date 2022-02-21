import * as React from 'react';
import { useState, useEffect } from 'react';
import ReactMapGL, { Marker } from 'react-map-gl';
import 'bootstrap/dist/css/bootstrap.css';

import coordData from "./MOCK_DATA.json";

import pin from "./icons/pin.png";

const api = 'https://api.mapbox.com/geocoding/v5/mapbox.places/';
const token = 'pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA';

const Pin = () => {
    return (
        <img src={pin} alt="pin" draggable={false} style={{ height: '60px', width: '40px'}} />
    )
}

const MyMarker = (props) => {
    const [coords, setCoords] = useState({ "lat": props.latitude, "long": props.longitude });

    function myOnDragEnd(ev) {
        setCoords({ long: ev.lngLat[0], lat: ev.lngLat[1] });
        console.log("New Marker Location: " + coords.lat + ', ' + coords.long);
    }

    return (
    <Marker longitude={coords.long} latitude={coords.lat} anchor="bottom" draggable={true} onDragEnd={ myOnDragEnd }>
                <Pin />
    </Marker>
    )
}

const DisplayMarker = (props) => {
    const [coords, setCoords] = useState({ "lat": -0, "long": -0 });

    useEffect(() => {
        if (props.latitude != null && props.longitude != null) {
            setCoords({"lat": props.latitude, "long": props.longitude});
        }else {
            getForwardGeocoding(props.address).then((data) => {
                setCoords({ "long": data.features[0].center[0], "lat": data.features[0].center[1] });
            });
        }
    }, [props.address, props.latitude, props.longitude]);

    return (
        <MyMarker longitude={coords.long} latitude={coords.lat}/>
    );
}

const getForwardGeocoding = async (search_text) => {
    const response = await fetch(api + search_text + '.json?access_token=' + token);
    const data = await response.json();
    return data;
}

const point = [40, 20];
const point2 = [-40, -20];
const locations = [point, point2];

function locz() {coordData.forEach(el => {
    locations.push([el.longitude, el.latitude]);
});} // for(let i = 0; i < 10; i++) {locations.push([i*5,i*-5])}}
locz();


const CreateTestMarkers = (locs) => {
    return locs.map((coord) => 
        <MyMarker longitude={coord[0]} latitude={coord[1]} />
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
                <div id="Markers">
                    <DisplayMarker address="Armstrong Student Center" />
                    {CreateTestMarkers(locations)}
                </div>
            </ReactMapGL>
        </div>
        );
}