import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './MapUI.css';

import { Container, Modal, Row } from 'react-bootstrap';

export default function UI() {
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
                        <Row>
                            <input type="text" className="form-control UI-Search" id="fromLocation" placeholder='Starting point'/>
                        </Row>
                        <Row>
                            <input type="text" className="form-control UI-Search" id="toLocation" placeholder='Ending point'/>
                        </Row>
                    </Container>
                </Modal.Body>
            </Modal.Dialog>
        </div>
    );
}