<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $email = $_POST['email'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM user_registration WHERE email='$email'";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();

    if (mysqli_num_rows($response) === 1) {

        $row = mysqli_fetch_assoc($response);

        if (password_verify($password, $row['password'])) {

            $index['fullname'] = $row['fullname'];
            $index['phNo'] = $row['phNo'];
            $index['UID'] = $row['UID'];
            $index['username'] = $row['username'];
            $index['email'] = $row['email'];
            $index['playlist_name'] = $row['playlist_name'];

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);
        }
    }
}
?>