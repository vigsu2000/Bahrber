import React, { Component } from 'react';
import {
  View,
  StyleSheet,
  Button,
  Image,
  Header,
  Left,
  Body,
  Right,
  Title,
  Text,
  PermissionsAndroid,
} from 'react-native'; //latest
import { StackNavigator } from 'react-navigation'; // Version can be specified in package.json
import t from 'tcomb-form-native'; // 0.6.19
import { ImagePicker } from 'expo';

const Form = t.form.Form;
var Thickness = t.enums({
  A: 'Bald',
  B: 'Thin',
  C: 'Thinner',
  D: 'Regular',
  E: 'Thick',
  F: 'To thick to run a comb',
})
var HairType = t.enums({
  A: 'Rough',
  B: 'Course',
  C: 'Smooth',
  D: 'Fine',
});
var DesiredLength = t.enums({
  A: 'Very long',
  B: 'Long',
  C: 'Medium',
  D: 'Short',
  E: 'Very short',
});
var Color = t.enums({
  A: 'Brown',
  B: 'Light brown',
  C: 'Blond',
  D: 'Dirty blond',
  E: 'Black',
  F: 'Red',
  G: 'Other',
});
const User = t.struct({
  //email: t.String,
  //username: t.maybe(t.String),
  //password: t.String,
  //gender: Gender,
  thickness: Thickness,
  hair_type: HairType,
  desired_length: DesiredLength,
  color: Color,
  terms: t.Boolean,
});
const formStyles = {
  ...Form.stylesheet,
  formGroup: {
    normal: {
      marginBottom: 10,
    },
  },
  controlLabel: {
    normal: {
      color: 'blue',
      fontSize: 18,
      marginBottom: 7,
      fontWeight: '600',
    },
    error: {
      color: 'red',
      fontSize: 18,
      marginBottom: 7,
      fontWeight: '600',
    },
  },
};

class HomeScreen extends React.Component {
  render() {
    return (
      <View style={styles.container0}>
        <Text>Welcome to Barhber</Text>
        <Button color = "black"
          title="Go to Application"
          onPress={() => this.props.navigation.navigate('Details')}
        />
      </View>
    );
  }
}

class DetailsScreen extends React.Component {
  render() {
    return (
      <View style={styles.container0}>
        <Text>Details Screen</Text>
        <Text>Maybe this page would be for creating an account</Text>
        <Button  color = "black"
          title="Go to details... again"
          onPress={() => this.props.navigation.push('Details')}
        />
        <Button color = "black"
          title="Go to Form"
          onPress={() => this.props.navigation.navigate('Application')}
        />
        <Button color = "black"
          title="Go back"
          onPress={() => this.props.navigation.goBack()}
        />
      </View>
    );
  }
}

class ApplicationScreen extends React.Component {
  render() {
    return (
      <View style={styles.container0}>
        <Text>Form Screen</Text>
        <Form ref={c => (this._form = c)} type={User}/>
        <Button color = "black"
          title = "Onward"
          //Will eventually do something
          />
        <Button color = "black"
          title="Go back"
          onPress={() => this.props.navigation.goBack()}
        />
      </View>
    )
  }
}

const RootStack = StackNavigator(
  {
    Home: {
      screen: HomeScreen,
    },
    Details: {
      screen: DetailsScreen,
    },
    Application: {
      screen: ApplicationScreen,
    },
  },
  {
    initialRouteName: 'Home',
  }
);

export default class App extends React.Component {
  render() {
    return <RootStack />;
  }
}

const styles = StyleSheet.create({
  container0: {
    fontWeight: '600',
    alignItems: 'center',
    justifyContent: 'center',
    flex: 1,
    color: 'black',
    marginTop: 0,
    padding: 20,
    backgroundColor: '#d3d3d3',
  },
});