import * as React from 'react';
import Map from './Map.js';
import UI from './MapUI.js';
import MapTypes from './mapTypes';


export default function App() {
    return  (
        <div>
            {/* Render UI alongside map and overlay */}
            <Map /> 
            <UI />
            <MapTypes />
        </div>
    )
}