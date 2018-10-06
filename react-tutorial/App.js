import React from "react"
import { StyleSheet, Text, View, Image, Button } from "react-native"
import Expo from "expo"

export default class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      signedIn: false,
      name: "",
      photoUrl: ""
    }
  }
  signIn = async () => {
    try {
      const result = await Expo.Google.logInAsync({
        androidClientId: "305422425542-5oc938tqe5rrn2a0c9bfiqbisdgvvr4h.apps.googleusercontent.com",
        scopes: ["profile", "email"]
      })

      if (result.type === "success") {
        this.setState({
          signedIn: true,
          name: result.user.name,
          photoUrl: result.user.photoUrl
        })
      } else {
        console.log("cancelled")
      }
    } catch (e) {
      console.log("error", e)
    }
  }
  render() {
    if (this.state.signedIn) {
      return <LoggedInPage name={this.state.name} photoUrl={this.state.photoUrl} />
    }
    else {
      return <LoginPage signIn={this.signIn} />
    }
  }
}

const LoginPage = props => {
  return (
    <View>
      <Button title="Sign in with Google" onPress={() => props.signIn()} />
    </View>
  )
}

const LoggedInPage = props => {
  return (
    <View>
      <Text> {props.name} </Text>
      <Image source={{ uri: props.photoUrl }} />
    </View>
  );
}

