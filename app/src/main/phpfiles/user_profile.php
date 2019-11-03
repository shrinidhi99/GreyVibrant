<?php
$conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    $username = $_POST['username'];
    $sql = "SELECT * FROM user_registration WHERE username='$username'";
    $response = mysqli_query($conn, $sql);
    $result = array();
    $result['profile'] = array();
    if (mysqli_num_rows($response) === 1) {
        $row = mysqli_fetch_assoc($response);
        $index['UID'] = $row['UID'];
        $UID = $row['UID'];
        $index['username'] = $row['username'];
        $index['fullname'] = $row['fullname'];
        $index['email'] = $row['email'];
        $index['phNo'] = $row['phNo'];
        $followquery = "SELECT COUNT(*) AS followcount FROM follow_artists WHERE UID='$UID'";
        $result1 = mysqli_query($conn, $followquery);
        $response1 = mysqli_fetch_assoc($result1);
        $index['followcount'] = $response1['followcount'];
        $listenquery = "SELECT DISTINCT COUNT(*) AS listencount FROM listens WHERE UID='$UID'";
        $result2 = mysqli_query($conn, $listenquery);
        $response2 = mysqli_fetch_assoc($result2);
        $index['listencount'] = $response2['listencount'];
        $playlistquery = "SELECT DISTINCT COUNT(*) AS playlistcount FROM playlist_name WHERE UID='$UID'";
        $result3 = mysqli_query($conn, $playlistquery);
        $response3 = mysqli_fetch_assoc($result3);
        $index['playlistcount'] = $response3['playlistcount'];
        array_push($result['profile'], $index);
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
?>