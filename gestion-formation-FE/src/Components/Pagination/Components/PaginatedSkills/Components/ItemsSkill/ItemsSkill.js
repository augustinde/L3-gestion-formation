import React from "react";
import {Table} from "react-bootstrap";

export default function ItemsSkill(props){
    return (
        <>
            <Table striped bordered hover variant={"dark"} responsive>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Compétences</th>
                    <th>Sous domaine</th>
                </tr>
                </thead>
                <tbody>
                {props.currentItems && props.currentItems.map((item) => (
                    <tr key={item.idSkill}>
                        <td>
                            {item.idSkill}
                        </td>
                        <td>{item.name}</td>
                        <td>{item.idSubdomain.name}</td>
                    </tr>
                ))}
                </tbody>
            </Table>

        </>
    );

}