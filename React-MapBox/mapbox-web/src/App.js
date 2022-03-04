import * as React from 'react';
import { useState, useEffect, useMemo } from 'react';
import ReactMapGL, { Marker, Source, Layer } from 'react-map-gl';
import 'bootstrap/dist/css/bootstrap.css';

import { API, TOKEN } from "./constants";
import coordData from "./MOCK_DATA.json";
import pin from "./icons/pin.png";


const Pin = () => {
    return (
        <img src={pin} alt="pin" draggable={false} height={'60px'} width={'40px'} />
    )
}

const DisplayMarker = (props) => {
    const [coords, setCoords] = useState({longitude: 0, latitude: 0}); 

    useEffect(() => {
        if (props.latitude != null && props.longitude != null) {
            setCoords([props.longitude, props.latitude]);
        }
        else {
            fetch(API + props.address + '.json?access_token=' + TOKEN)
            .then(resp => resp.json())
            .then(json => {
                setCoords({longitude: json.features[0].center[0], latitude: json.features[0].center[1]});
            })
        }
    }, [coords.longitude, coords.latitude, props.address, props.longitude, props.latitude]);

    return (
        <Marker 
            longitude={coords.longitude} 
            latitude={coords.latitude} 
            anchor="bottom" 
            draggable={true} 
            onDragEnd={ ev => setCoords({longitude: ev.lngLat[0], latitude: ev.lngLat[1]})}>
            <Pin />
        </Marker>
    );
}

// const CreateTestMarkers = () => {
//     return coordData.map((coord, index) => 
//         <MyMarker className={"Marker"+index} key={index} longitude={coord.longitude} latitude={coord.latitude} />
//     );
// }

/*
const data = useMemo(() => {
    return coords; 
}, [coords]);


<Source type="geojson" data={data}>
                    <Layer {...heatmapLayer} />
                </Source>
*/

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
                mapboxApiAccessToken={TOKEN}
                mapStyle="mapbox://styles/nicokasz/ckz23hv99001w14qmii9m7ac4"
                onViewportChange={(viewport) => { setViewport(viewport); }}
            >

                

                <div id="Markers">
                    <DisplayMarker address="Armstrong Student Center" />
                    
                </div>
            </ReactMapGL>
        </div>
        );
}