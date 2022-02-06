import * as React from 'react';
import { useState } from 'react';
import ReactMapGL, { Marker } from 'react-map-gl';


function getForwardGeocoding(search_text) {
    console.log(encodeURI('https://api.mapbox.com/geocoding/v5/mapbox.places/' + search_text + '.json?access_token=pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA'));
    return fetch(encodeURI('https://api.mapbox.com/geocoding/v5/mapbox.places/' + search_text + '.json?access_token=pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA'))
        .then((response) => response.json())
        .then((responseJson) => {
            return responseJson;
        })
        .catch((error) => {
            console.error(error);
        });
}

async function jsonData(search_text) { // (1)
    let res = await fetch(encodeURI('https://api.mapbox.com/geocoding/v5/mapbox.places/' + search_text + '.json?access_token=pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MGN4djV5MWFuczJwcDFwYW0zMWxhZyJ9.aOdFpJtgU4ow7ascIJDXQA')); // (2)

    if (res.status === 200) {
        let json = await res.json(); // (3)
        return json;
    }

    throw new Error(res.status);
}

function makeMarker(long, lat) {
    return (
        <Marker longitude={long} latitude={lat} anchor="bottom" draggable={true} >
            <img src="pin.png" alt="pin"/>
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
        //style: 'mapbox://styles/mapbox/streets-v11'
    });

    var o = jsonData('241 Eisenhower Way').then();
    console.log(o);
  

    //var obj = getForwardGeocoding('9481 Chesapeake Drive').then(responseJson => {return responseJson});
    //console.log(obj);
    //var myObject = JSON.parse(obj);
    //console.log(myObject);

    return (
        
        <ReactMapGL
            {...viewport}
            mapboxApiAccessToken="pk.eyJ1Ijoibmljb2thc3oiLCJhIjoiY2t6MjE2NXprMDF4
czJ2b21uZjhqOXlhaCJ9.pzhG-dabniu4rtlDnkIVjw"
            mapStyle="mapbox://styles/nicokasz/ckz23hv99001w14qmii9m7ac4"
            onViewportChange={(viewport) => { setViewport(viewport); }}
            >
                {makeMarker( -85, 40)}

            </ReactMapGL>
        );
} 