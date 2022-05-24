import React from "react";
import { Form, Container, Modal, Row, Col, Button } from "react-bootstrap";
import { setAddress, changeHeatmap } from "./Map";
import "bootstrap/dist/css/bootstrap.min.css";
import "../Assets/CSS/MapUI.css";

// Object Array of Map Types and where their sources are - TODO replace with api endpoint
import mapTypes from "./mapTypes";

export default function UI() {
  function handleSubmit(event) {
    // Sends values to Map
    // - after checking if values are null/empty
    if (event.target[0].value !== null && event.target[0].value !== "")
      setAddress(0, event.target[0].value);

    if (event.target[2].value !== null && event.target[2].value !== "")
      setAddress(1, event.target[2].value);

    // Stop script from refreshing page
    event.preventDefault();
    // Print Submitted
    console.log("updating start & end markers");
  }

  return (
    <div className="UI">
      <Modal.Dialog className="UI-Left">
        <Modal.Header>
          <Modal.Title>
            <p className="UI-Modal-Title">
                <Button> &lt; </Button>
                MyPath
            </p>
          </Modal.Title>
        </Modal.Header>

        <Modal.Body>
          <Container>
            <Form onSubmit={handleSubmit}>
              <Row>
                <input
                  type="text"
                  className="form-control UI-Search"
                  id="fromLocation"
                  placeholder="Starting point"
                />
                <input
                  type="submit"
                  className="btn-primary UI-Submit"
                  value="Search"
                />
              </Row>
              <Row>
                <input
                  type="text"
                  className="form-control UI-Search"
                  id="toLocation"
                  placeholder="Ending point"
                />
              </Row>
            </Form>
          </Container>
        </Modal.Body>
      </Modal.Dialog>

      <Modal.Dialog className="UI-Heatmap" id="mooodal">
        <Modal.Header>
          <Modal.Title>
            <p className="UI-Modal-Title">Layers</p>
          </Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Row>
            {mapTypes.map(function (map) {
              return (
                <Col md={4} key={map.key} className="Heatmap-Col">
                  <Button
                    onClick={() => {
                      changeHeatmap(map.src);
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
        </Modal.Body>
      </Modal.Dialog>
    </div>
  );
}
