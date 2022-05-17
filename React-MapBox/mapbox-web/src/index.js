import React from 'react';
import ReactDOM from 'react-dom';
import './Assets/CSS/index.css';
import App from './Components/App';
import Route from './Components/route.js';

ReactDOM.render(
  <React.StrictMode>
        <div>       {/* Render UI alongside map and overlay */}
            <App  />
            <Route/>
        </div>
  </React.StrictMode>,
  document.getElementById('root')
);
