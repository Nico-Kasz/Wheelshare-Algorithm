import * as React from 'react';
import { useState, useEffect, useRef} from 'react';
import ReactMapGL, { GeolocateControl, Marker, Source, Layer } from 'react-map-gl';
import {heatmapLayer} from './map-style';



// Constants and Image Imports
import { API, TOKEN } from "./constants";
import pin from "./Assets/Images/pin.png";

// UI specific imports
import 'bootstrap/dist/css/bootstrap.css';
import './MapUI.css';
import { Form, Container, Modal, Row } from 'react-bootstrap';

// Bad way to make sure there is an update when search is hit again
let count = 0;



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
                console.log("update: " + count);
                if (json.features[0].center[1] !== undefined)           // Just incase
                    setCoords({longitude: json.features[0].center[0], latitude: json.features[0].center[1]});
            })
        }
    }, [props.address, props.longitude, props.latitude, count]);

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

export default function App() {
    const startRef = useRef(null);
    const endRef = useRef(null);


    function UI() {

        function handleSubmit(event) {
            // Set Refs such to display markers
            if (event.target[0].value !== null && event.target[0].value !== '')
                startRef.current = event.target[0].value;

            if (event.target[2].value !== null && event.target[2].value !== '')
                endRef.current = event.target[2].value;
            count++;
            // Stop script from refreshing page
            event.preventDefault(); 
            // Print Submitted
            console.log("updating marker");

        }

        return (
            <div >
                <Modal.Dialog className="UI-Upper-Left">
                    <Modal.Header>
                        <Modal.Title>
                            Wheelshare
                        </Modal.Title>
                    </Modal.Header>

                    <Modal.Body>
                        <Container >
                            <Form onSubmit={handleSubmit}>
                            <Row>
                                <input type="text" className="form-control UI-Search" id="fromLocation" placeholder='Starting point'/>
                                <input type="submit" className='btn-primary UI-Submit'  value="Search" />
                            </Row>
                            <Row>
                                <input type="text" className="form-control UI-Search" id="toLocation" placeholder='Ending point'/>
                            </Row>
                            </Form>
                        </Container>
                    </Modal.Body>
                </Modal.Dialog>
            </div>
        );
    }


    function Map() {
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
        
        const data =  require('./Assets/Geojsons/4.20.22 gps data.geojson');//require('./data.geojson');

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
                        <DisplayMarker address={startRef.current}/>
                        <DisplayMarker address={endRef.current}/>
                    </div>
                    
                    {/* <PullOSM/> */}

                    {<GeolocateControl trackUserLocation={true} maxZoom={22}/>}
                    
                </ReactMapGL>
            </div>
            );
    }
    let renderMap = false;
    return  !renderMap? (<UI/>) : (
        <div>
            <Map />
            <UI />
        </div>
    )
}