import { CircularProgress } from "@mui/material";
import {useEffect, useMemo, useState } from "react";
import {Route, Routes} from "react-router-dom";
import {apiClient} from "./api/apiClient";
import {Landing} from "./components/Landing";
import {Layout} from "./components/Layout";
import {Content} from "./components/Content";
import {NotFoundPage} from "./components/NotFoundPage";
import { PrivateRoute } from "./components/PrivateRoute";
import {Redirect} from "./components/Redirect";
import { AlertMessageProvider } from "./context/AlertMessageContext";
import {UserContext} from "./context/UserContext";
import {User} from "./types/types";

export const App = () => {

  //current user data
  const [user, setUser] = useState<Partial<User>>({});
  const [userLoading, setUserLoading] = useState(true);
  const handleChangeUser = (user: User) => {
    setUser(user);
  };
  const userContextValue = useMemo(
      () => ({ user, handleChangeUser }),
      [user]
  );

  useEffect(() => {
    if (location.pathname === "/authorized") {
      setUserLoading(false);
      return;
    }
    apiClient().get("/currentUser")
        .then((res) => {
          if (res.data) {
            setUser(res.data);
            setUserLoading(false);
          } else {
            setUserLoading(false);
          }
        }).catch((err) => {
          console.error(err);
          setUserLoading(false);
    });
  }, []);


  return (
      <AlertMessageProvider>
        <UserContext.Provider value={userContextValue}>{
          userLoading ? (
              <CircularProgress color="primary" sx={{position: "absolute", top: "50%", right: "50%"}}/>
          ) : (
              <Routes>
                <Route path="/redirect" element={<Redirect/>}/>
                <Route path="/authorized" element={<Redirect/>}/>
                <Route path="/landing" element={<Landing/>}/>
                <Route path="/" element={<PrivateRoute/>}>
                  <Route path="/" element={<Layout/>}>
                    <Route index element={<Content/>}/>
                  </Route>
                </Route>
                <Route path="*" element={<NotFoundPage/>}/>
              </Routes>
          )
        }
        </UserContext.Provider>
      </AlertMessageProvider>
  );
};
