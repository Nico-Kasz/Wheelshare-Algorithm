import * as React from "react";
import { parseString } from "xml2js";
import raw from "raw.macro";

export default function Route() {
    // Import xml map data as raw text
    const osm = raw("../Assets/XML/map.xml");

    // Parse xml into Json "results"
    parseString(osm, function (err, results) {
        // Print entire Json
        console.log(results);

        // Create constant for all Nodes[]
        const Nodes = results.osm.node;

        // Create constant for all relationships[]
        const Relate = results.osm.relation;

        // Create constant for all Ways[]
        const Ways = results.osm.way;

        // Counts how many Ways have a labeled slope or incline and gets average slope of all calculated
        let count = 0;
        let sum = 0;
        Ways.forEach((way) => {
            try {
                way.tag.forEach((tag) => {
                    // Check for attribute "incline" or "slope" => some return "up" or "down"
                    if (
                        (tag.$.k === "incline" || tag.$.k === "slope") &&
                        !isNaN(tag.$.v)
                    ) {
                        count++;
                        sum += Number(tag.$.v);
                        //console.log("Incline:" + tag.$.v);
                    }
                });
                // way does not have any tags
            } catch (error) {
                /*console.log(error);*/
            }
        });
        console.log(
            "Number of measured slopes: " +
                count +
                "\nAverage slope: " +
                sum / count
        );
    });
    return (
        <div />
    ); /*(<h1 style={{textAlign: "center"}}>Running Routing in background</h1>);*/
}
