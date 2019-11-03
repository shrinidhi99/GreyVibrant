        <?php
        $conn = mysqli_connect("localhost", "id11221849_sabios", "Sayan@99", "id11221849_greyvibrant");
        if ($_SERVER['REQUEST_METHOD'] == 'POST') {

            $username = $_POST['username'];
            $email = $_POST['email'];
            $password = $_POST['password'];
            $password = password_hash($password, PASSWORD_DEFAULT);


            $sql = "SELECT UID FROM user_registration WHERE username='$username'";

            $response = mysqli_query($conn, $sql);
            
            if(!is_null($response))
            {
                $sqlreg="UPDATE user_registration SET password='$password' WHERE username='$username' AND email='$email'";
                $sqllog="UPDATE user_login SET password='$password' WHERE username='$username'";

                if(mysqli_query($conn, $sqlreg) && mysqli_query($conn, $sqllog) )
                {
                 $result['success'] = "1";
                 $result['message'] = "success";
                }
                else
                {
                 $result['success'] = "0";
                 $result['message'] = "networkerror";
                }

                
            }
            else
            {
                $result['success'] = "2";
                $result['message'] = "userexists";
            }



                   
            echo json_encode($result);
            mysqli_close($conn);

            
        }
        ?>