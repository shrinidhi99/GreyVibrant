    <?php
    $conn = mysqli_connect("localhost","id11221849_sabios","Sayan@99","id11221849_greyvibrant");
    if ($_SERVER['REQUEST_METHOD']=='POST') {

        $UID = $_POST['UID'];
        $AID = $_POST['AID'];

        $sqldel="DELETE FROM follow_artists WHERE UID=$UID AND AID=$AID";
        
        
        if ( mysqli_query($conn, $sqldel)) {

           
            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        }else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);

        }

    }

    ?>