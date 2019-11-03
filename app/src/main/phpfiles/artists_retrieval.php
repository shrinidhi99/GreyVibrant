<?php
    $conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        $UID = $_POST['UID'];
        $sql1 = "SELECT A.AID , A.artistname FROM artist_registration A, follow_artists F  WHERE A.AID = F.AID AND F.UID=$UID";
        $response1 = mysqli_query($conn, $sql1);
        $result = array();
        $result['folartist'] = array();
        $result['unfolartist'] = array();

        while ($row1 = mysqli_fetch_assoc($response1)) {
            $index['AID'] = $row1['AID'];
            $index['artistname'] = $row1['artistname'];

            array_push($result['folartist'], $index);
        }

        $sql2 = "SELECT A.AID , A.artistname FROM artist_registration A WHERE A.AID NOT IN (SELECT F.AID FROM follow_artists F WHERE F.UID=$UID)";
        $response2 = mysqli_query($conn, $sql2);

        while ($row2 = mysqli_fetch_assoc($response2)) {
            $index['AID'] = $row2['AID'];
            $index['artistname'] = $row2['artistname'];

            array_push($result['unfolartist'], $index);
        }

        if (mysqli_num_rows($response1) > 0 || mysqli_num_rows($response2) > 0) {
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