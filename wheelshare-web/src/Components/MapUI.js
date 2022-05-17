import React from 'react';
import {useRef} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../Assets/CSS/MapUI.css';

import { Form, Container, Modal, Row } from 'react-bootstrap';


export default function UI() {

    const startRef = useRef(null);
    const endRef = useRef(null);
 
    function handleSubmit(event) {
        // Set Refs such to display markers
        // - after checking if values are null/empty
        if (event.target[0].value !== null && event.target[0].value !== '')
            startRef.current = event.target[0].value;

        if (event.target[2].value !== null && event.target[2].value !== '')
            endRef.current = event.target[2].value;

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