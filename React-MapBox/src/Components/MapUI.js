import React from "react";
import { Form, Container, Row, Col, Button } from "react-bootstrap";
import { setAddress } from "./Map";
import "bootstrap/dist/css/bootstrap.min.css";
import "../Assets/CSS/MapUI.css";


function handleSubmit(event) {
    // Sanitation happens before fetching
    setAddress(0, event.target[0].value);
    setAddress(1, event.target[2].value);

  // Stop script from refreshing page
  event.preventDefault();
}

function swapSearchTerms() {
  const fromRef = document.getElementById("fromLocation");
  const toRef = document.getElementById("toLocation");

  let temp = fromRef.value;

  fromRef.value = toRef.value;
  toRef.value = temp;
}


export default function UI() {
  // TODO - Create multiple Menus and ability to switch between them
  return (
    <div id="UI">
      <div id="UI-Left">
        <div id="UI-Title">
          <Button className="Back-Arrow-Header"> &lt; </Button>
          MyPath
        </div>
        <div id="UI-Content">
          <Container>
            <Form onSubmit={handleSubmit}>
              <Row>
                <Col md={8}>
                  <input
                    type="text"
                    className="form-control UI-Search"
                    id="fromLocation"
                    placeholder="Starting point"
                  />
                </Col>
                <Col md={4}>
                  <Button
                    className="UI-Submit"
                    onClick={() => {
                      swapSearchTerms();
                    }}
                  >
                    Swap
                  </Button>
                </Col>
              </Row>
              <Row>
                <Col md={8}>
                  <input
                    type="text"
                    className="form-control UI-Search"
                    id="toLocation"
                    placeholder="Ending point"
                  />
                </Col>
                <Col md={4}>
                  <input
                    type="submit"
                    className="btn-primary UI-Submit"
                    value="Search"
                  />
                </Col>
              </Row>
            </Form>
          </Container>
          <div className="Slider-Text">Slope Tolerance:</div>
          <Row>
            <input
              id="typeinp"
              className="Slider"
              type="range"
              min="0"
              max="15"
              defaultValue="3"
              step=".2"
              onChange={(event) => {
                console.log(event.target.value);
              }}
            />
          </Row>
          <Row>
            <Button className="Account-Button">Log In</Button>
          </Row>
          <Row>
            <Button className="Account-Button">Sign Up</Button>
          </Row>
          <div className="Footer-Info">
            <Row>
              <Col xs={4}>
                <a href="http://routemypath.com" target="_blank" rel="noopener noreferrer">
                  About
                </a>
              </Col>
              <Col xs={4}>Feedback</Col>
              <Col xs={4}>
                <a href="mailto:someone@yoursite.com">Contact Us</a>
              </Col>
            </Row>
          </div>
        </div>
      </div>
    </div>
  );
}
