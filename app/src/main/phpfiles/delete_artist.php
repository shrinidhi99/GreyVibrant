<?php

$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $AID = $_POST['AID'];
    $sql1 = "DELETE FROM song WHERE SID IN (SELECT SID FROM song_artists WHERE AID='$AID')";
    $sql = "DELETE FROM artist_registration WHERE AID='$AID'";
    $flag = mysqli_query($conn, $sql1);
    if ($flag) {
        if (mysqli_query($conn, $sql)) {
            $result["success"] = "1";
            $result["message"] = "success";
            echo json_encode($result);
            mysqli_close($conn);
        }
    } else {
        if (!$flag) {
            $result["success"] = "0";
            $result["message"] = "Error in first query";
        } else {
            $result["success"] = "0";
            $result["message"] = "Error in second query";
        }
        echo json_encode($result);
        mysqli_close($conn);
    }
}
