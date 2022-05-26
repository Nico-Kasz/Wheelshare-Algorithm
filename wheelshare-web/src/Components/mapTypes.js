import React from "react";
import { Row, Col, Button } from "react-bootstrap";
import { changeHeatmap } from "./Map";
import "../Assets/CSS/MapTypes.css"

const mapLayers = [
  {
    name: "normal",
    key: "default",
    imgSrc: require("../Assets/Images/web-icon.png"),
    src: null,
  },
  {
    name: "heatmap",
    key: "option 1",
    imgSrc:require("../Assets/Images/web-icon.png"),
    src: require("../Assets/Geojsons/4.20.22 gps data.geojson"),
  }
];

const MapTypes = () => {
  return (
    <div id="UI-Heatmap">
      <input type="checkbox" className="Heatmap-Toggle" id="Heatmap-Toggle" />
      <label for="Heatmap-Toggle" className="Heatmap-Toggle-Icon">
        <img
          src={require("../Assets/Images/web-icon.png")}
          alt="Map Toggle Icon"
          width="100%"
          height="100%"
        />
      </label>

      <div id="Heatmap-Content">
        <Row>
          {mapLayers.map(function (map) {
            return (
              <Col sm={"auto"} key={map.key}>
                <Button
                  onClick={() => {
                    changeHeatmap(map.src);
                    document.getElementById("Heatmap-Toggle").checked = false;
                  }}
                  className="Heatmap-Button"
                >
                  <img
                    src={map.imgSrc}
                    alt={map.name}
                    className="Heatmap-Button-Icon"
                  />
                  <p className="HeatMap-Button-Text">{map.name}</p>
                </Button>
              </Col>
            );
          })}
        </Row>
      </div>
    </div>
  );
};

export default MapTypes;
