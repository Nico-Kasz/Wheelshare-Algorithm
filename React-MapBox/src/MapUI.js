import React, {useState} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './MapUI.css';

import { Form, Container, Modal, Row } from 'react-bootstrap';


export default function MapUI() {

    const [start, setStart] = useState('');
    const [end, setEnd] = useState('');

    function handleStartChange(event) {
        setStart(event.target.value);
        console.log(start);
    }

    function handleEndChange(event) {
        setEnd(event.target.value);
        console.log(end);
    }

    function handleSubmit(event) {
        // Stop script from refreshing page
        event.preventDefault(); 
        // Print Submitted
        alert("submitted");
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
                            <input type="text" className="form-control UI-Search" id="fromLocation" placeholder='Starting point' onChange={handleStartChange}/>
                            <input type="submit" className='btn-primary UI-Submit'  value="Search" />
                        </Row>
                        <Row>
                            <input type="text" className="form-control UI-Search" id="toLocation" placeholder='Ending point' onChange={handleEndChange}/>
                        </Row>
                        </Form>
                    </Container>
                </Modal.Body>
            </Modal.Dialog>
        </div>
    );
}