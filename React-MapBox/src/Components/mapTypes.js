import React from "react";
import { Row, Col, Button } from "react-bootstrap";
import { changeOverlay } from "./Map";
import "../Assets/CSS/MapTypes.css";

const mapLayers = [
    {
        name: "normal",
        key: "default",
        imgSrc: require("../Assets/Images/web-icon.png"),
        src: null,
    },
    {
        name: "sidewalk data",
        key: "option 1",
        imgSrc: require("../Assets/Images/web-icon.png"),
        src: require("../Assets/Geojsons/sidewalk.geojson"),
    },
];

const MapTypes = () => {
    return (
        <div id="UI-Overlay">
            <input
                type="checkbox"
                className="Overlay-Toggle"
                id="Overlay-Toggle"
            />
            <label htmlFor="Overlay-Toggle" className="Overlay-Toggle-Icon">
                <img
                    src={require("../Assets/Images/web-icon.png")}
                    alt="Map Toggle Icon"
                    width="100%"
                    height="100%"
                />
            </label>

            <div id="Overlay-Content">
                <Row>
                    {mapLayers.map(function (map) {
                        return (
                            <Col sm={"auto"} key={map.key}>
                                <Button
                                    onClick={() => {
                                        changeOverlay(map.src);
                                        document.getElementById(
                                            "Overlay-Toggle"
                                        ).checked = false;
                                    }}
                                    className="Overlay-Button"
                                >
                                    <img
                                        src={map.imgSrc}
                                        alt={map.name}
                                        className="Overlay-Button-Icon"
                                    />
                                    <p className="Overlay-Button-Text">
                                        {map.name}
                                    </p>
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
