import React from 'react';
import ReactDOM from 'react-dom';
import { createContext } from 'react';
import './index.css';
import Map from './App';
import UI from './MapUI';
import reportWebVitals from './reportWebVitals';

const startContext = createContext();
const endContext = createContext();

ReactDOM.render(
  <React.StrictMode>
        <div>       {/* Render UI alongside map and overlay */}
            <Map start = {startContext}/>
            <UI  end   = {endContext}/>
        </div>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
