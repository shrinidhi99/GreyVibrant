<?php

$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $fullname = $_POST['fullname'];
    $phNo = $_POST['phNo'];
    $artistname = $_POST['artistname'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    $password = password_hash($password, PASSWORD_DEFAULT);

    $sql = "INSERT INTO artist_registration VALUES (NULL, '$artistname', '$fullname', '$email', '$password', '$phNo')";

    if (mysqli_query($conn, $sql)) {

        $AIDsql = "SELECT AID FROM artist_registration WHERE artistname = '$artistname'";

        $response = mysqli_query($conn, $AIDsql);

        $row = mysqli_fetch_object($response);

        $sql1 = "INSERT INTO artist_login VALUES ($row->AID,'$artistname','$password')";

        if (mysqli_query($conn, $sql1)) {

            $result["success"] = "1";
            $result["message"] = "success";

            echo json_encode($result);
            mysqli_close($conn);

        }

    } else {

        $result["success"] = "0";
        $result["message"] = "Either user already exists or check your internet connection";

        echo json_encode($result);
        mysqli_close($conn);
    }
}
