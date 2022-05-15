import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import Route from './Routing/route.js';

ReactDOM.render(
  <React.StrictMode>
        <div>       {/* Render UI alongside map and overlay */}
            <App  />
            <Route/>
        </div>
  </React.StrictMode>,
  document.getElementById('root')
);
