import React from "react";
import { Form, Container, Row, Col, Button } from "react-bootstrap";
import { setAddress } from "./Map";
import "bootstrap/dist/css/bootstrap.min.css";
import "../Assets/CSS/MapUI.css";

const AccountInfo = { loggedIn: false };

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

const DisplayProfile = () => {
  if (AccountInfo.loggedIn) {
    return <div id="Account"></div>;
  } else {
    return (
      <div id="SignIn">
        <Row>
          <Button className="Account-Button">Log In</Button>
        </Row>
        <Row>
          <Button className="Account-Button">Sign Up</Button>
        </Row>
      </div>
    );
  }
};

export default function UI() {
  // TODO - Create multiple Menus and ability to switch between them
  return (
    <div id="UI">
      <div id="UI-Left" className="UI-Left">
        <div id="UI-Title">MyPath</div>
        <div id="UI-Content">
          <Container>
            <Form onSubmit={handleSubmit}>
              <div id="Search-Area">
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
                    <Button
                      type="submit"
                      className="btn-primary UI-Submit"
                    >
                    Search
                    </Button>
                  </Col>
                </Row>
              </div>
            </Form>
          </Container>
          <div id="Slider">
            <div className="Slider-Text">Slope Tolerance:</div>
            <Row>
              <input
                id="InclineSlider"
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
          </div>
          <div id="Profile">{DisplayProfile()}</div>
          <div id="Footer-Info">
            <Row>
              <Col md={4}>
                <a
                  href="http://routemypath.com"
                  target="_blank"
                  rel="noopener noreferrer"
                  className="footerBtn btn-primary"
                >
                  About
                </a>
              </Col>
              <Col md={4}>
                <Button
                  onClick={() => {
                    let Panel = document.getElementById("Feedback");
                    Panel.classList.remove("Feedback-Hidden");
                  }}
                  className="footerBtn"
                >
                  Feedback
                </Button>
              </Col>
              <Col md={4}>
                <a href="mailto:raychov@miamioh.edu" className="footerBtn btn-primary">Contact Us</a>
              </Col>
            </Row>
          </div>
        </div>
        <Button
          className="Back-Arrow-Header"
          onClick={() => {
            let UI = document.getElementById("UI-Left");
            let text = document.getElementById("Back-Arrow-Text");
            if (UI.classList.contains("UI-Left-Hidden")) {
              UI.classList.remove("UI-Left-Hidden");
              text.classList.remove("Back-Arrow-Text-Flipped");
            } else {
              UI.classList.add("UI-Left-Hidden");
              text.classList.add("Back-Arrow-Text-Flipped");
            }
          }}
        >
          <p className="Back-Arrow-Text" id="Back-Arrow-Text">
            &lt;
          </p>
        </Button>
        <div id="Feedback" className="Feedback Feedback-Hidden">
          <Form
            onSubmit={(event) => {
              event.preventDefault();
              console.log(event.target[0].value);
              let Panel = document.getElementById("Feedback");
              Panel.classList.add("Feedback-Hidden");
            }}
          >
            <textarea className="form-control Feedback-Text" rows={6} />
            <Row>
              <Col md={6}>
                <Button
                  className="Feedback-Button"
                  onClick={() => {
                    let Panel = document.getElementById("Feedback");
                    Panel.classList.add("Feedback-Hidden");
                  }}
                >
                  {" "}
                  Close
                </Button>
              </Col>
              <Col md={6}>
                <Button type="submit" className="Feedback-Button">
                  Send Feedback
                </Button>
              </Col>
            </Row>
          </Form>
        </div>
      </div>
    </div>
  );
}
