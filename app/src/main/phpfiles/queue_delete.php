<?php

$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $SID = $_POST['SID'];

    $sql = "DELETE FROM queue WHERE SID='$SID'";

    if (mysqli_query($conn, $sql)) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
    } else {

        $result["success"] = "0";
        $result["message"] = "Error";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
