<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $artistname = $_POST['artistname'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $password = password_hash($password, PASSWORD_DEFAULT);
    $sql = "SELECT AID FROM artist_registration WHERE artistname='$artistname'";
    $response = mysqli_query($conn, $sql);
    if (!is_null($response)) {
        $sqlreg = "UPDATE artist_registration SET password='$password' WHERE artistname='$artistname' AND email='$email'";
        $sqllog = "UPDATE artist_login SET password='$password' WHERE artistname='$artistname'";
        if (mysqli_query($conn, $sqlreg) && mysqli_query($conn, $sqllog)) {
            $result['success'] = "1";
            $result['message'] = "success";
        } else {
            $result['success'] = "0";
            $result['message'] = "networkerror";
        }
    } else {
        $result['success'] = "2";
        $result['message'] = "artist not exists";
    }
    echo json_encode($result);
    mysqli_close($conn);
}
