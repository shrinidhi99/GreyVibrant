<?php

$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");

if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $fullname = $_POST['fullname'];
    $phNo = $_POST['phNo'];
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $playlist_name = $_POST['playlist_name'];

    $password = password_hash($password, PASSWORD_DEFAULT);

    $sql = "INSERT INTO user_registration  VALUES (NULL,'$fullname','$phNo','$username','$email','$password','$playlist_name')";

    if (mysqli_query($conn, $sql)) {

        $UIDsql = "SELECT UID FROM user_registration WHERE username = '$username'";

        $response = mysqli_query($conn, $UIDsql);

        $row = mysqli_fetch_object($response);

        $sql1 = "INSERT INTO user_login  VALUES ($row->UID,'$username','$password')";

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

?>