import React from 'react';
import { Form, Container, Modal, Row } from 'react-bootstrap';
import { setStartAddress, setEndAddress} from './Map'
import 'bootstrap/dist/css/bootstrap.min.css';
import '../Assets/CSS/MapUI.css';


export default function UI() {
 
    function handleSubmit(event) {
        // Set Refs such to display markers
        // - after checking if values are null/empty
        if (event.target[0].value !== null && event.target[0].value !== '')
            setStartAddress(event.target[0].value);

        if (event.target[2].value !== null && event.target[2].value !== '')
            setEndAddress(event.target[2].value);

        // Stop script from refreshing page
        event.preventDefault(); 
        // Print Submitted
        console.log("updating markers");

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