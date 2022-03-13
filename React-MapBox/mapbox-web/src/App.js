import * as React from 'react';
import { useState, useEffect } from 'react';
import ReactMapGL, { GeolocateControl, Marker, Source, Layer } from 'react-map-gl';
import 'bootstrap/dist/css/bootstrap.css';
import {heatmapLayer} from './map-style';
import { parseString } from "xml2js"; 

import { API, TOKEN } from "./constants";
//import coordData from "./MOCK_DATA.json";
import pin from "./icons/pin.png";


const Pin = () => {
    return (
        <img src={pin} alt="pin" draggable={false} height={'60px'} width={'40px'} />
    )
}

const DisplayMarker = (props) => {
    const [coords, setCoords] = useState({longitude: 0, latitude: 0}); 

    useEffect(() => {
        if (!(props.latitude == null || props.longitude == null)) {
            setCoords({longitude: props.longitude, latitude: props.latitude});
        }
        else {
            // PARSE ADDRESS TODO
            fetch(API + props.address + '.json?access_token=' + TOKEN)
            .then(resp => resp.json())
            .then(json => {
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
            draggable={true} 
            onDragEnd={ ev => markerOnDragEvent(ev)}>
            <Pin />
        </Marker>
    );
}

const PullOSM = (props) => {
    console.log('pulling osm');
    useEffect(() => {
        fetch('https://overpass-api.de/api/map?bbox=-84.74498,39.50838,-84.73334,39.51204')
        .then((response) => response.text())
        .then((textResponse) => {
            // parsing xml data
            parseString(textResponse, function (err, results) {
                
            console.log(results);
            console.log(results.osm.way.length);


            for(let i = 0; i < results.osm.way.length; i++) {
                for(let j = 0; j < results.osm.way[i].tags.length; j++) {
                    let k = results.osm.way[i].tags[j].k;
                    if (k === "slope" || k === "incline") {
                        console.log(results.osm.way[i].tags[j].v);
                        break;
                    }
                }
            }
            });
        })
    }, []);
    return <div/>
}


// const CreateTestMarkers = () => {
//     return coordData.map((coord, index) => 
//         <MyMarker className={"Marker"+index} key={index} longitude={coord.longitude} latitude={coord.latitude} />
//     );
// }

export default function Map() {
    const [viewport, setViewport] = useState({
        width: '100vw',
        height: '100vh',
        hash: true,
        latitude: 39.50882818527073,
        longitude: -84.73455522976074,
        zoom: 14,
        minPitch: 0,
        maxPitch: 30
    });
    
    const data =  null;//require('./data.geojson');
    
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
                    <DisplayMarker address="Armstrong Student Center" />
                </div>
                
                <PullOSM/>

                <GeolocateControl trackUserLocation={true}/>
                
            </ReactMapGL>
        </div>
        );
}