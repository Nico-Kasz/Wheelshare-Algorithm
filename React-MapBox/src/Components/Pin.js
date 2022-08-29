import React from "react";
import pin from "../Assets/Images/pin.png";

const Pin = () => {
    return (
        <img
            src={pin}
            alt="pin"
            draggable={false}
            height={"60px"}
            width={"40px"}
            style={{ position: "fixed", bottom: 0, right: -20 }}
        />
    );
};

export default Pin;
