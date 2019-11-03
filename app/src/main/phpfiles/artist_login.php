<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $artistname = $_POST['artistname'];
    $password = $_POST['password'];

    $sql = "SELECT * FROM artist_login WHERE artistname='$artistname'";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();

    if (mysqli_num_rows($response) === 1) {

        $row = mysqli_fetch_assoc($response);

        if (password_verify($password, $row['password'])) {

            $index['AID'] = $row['AID'];
            $index['artistname'] = $row['artistname'];

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