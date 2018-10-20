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
} from 'react-native';
import { createStackNavigator } from 'react-navigation';
import t from 'tcomb-form-native'; // 0.6.19
import { ImagePicker } from 'expo';

const Form = t.form.Form;
//Thckness, length, style, texture
var Gender = t.enums({
  M: 'Male',
  F: 'Female',
  O: 'Not applicable',
});

var HairType = t.enums({
  A: 'Rough',
  B: 'Course',
  C: 'Smooth',
  D: 'Fine',
});

var Length = t.enums({
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
  gender: Gender,
  hair_type: HairType,
  desired_length: Length,
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
    // the style applied when a validation error occours
    error: {
      color: 'red',
      fontSize: 18,
      marginBottom: 7,
      fontWeight: '600',
    },
  },
};

const options = {
  fields: {
    email: {
      error:
        'Without an email address how are you going to reset your password when you forget it?',
    },
    password: {
      error:
        "Choose something you use on a dozen other sites or something you won't remember",
    },
    terms: {
      label: 'Agree to Terms',
    },
  },
  stylesheet: formStyles,
};

export default class HomeScreen extends Component {
  state = {
    image: null,
  };

  handleSubmit = () => {
    const value = this._form.getValue();
    console.log('value: ', value);
  };

  static navigationOptions = {
    title: 'Details',
  };

  render() {
    let { image } = this.state;

    return (
      <View style={styles.container0}>
        <Form ref={c => (this._form = c)} type={User} options={options} />
        <Button
          title="Pick an image from camera roll"
          color="blue"
          onPress={this._pickImage}
        />
        {image && (
          <Image source={{ uri: image }} style={{ width: 200, height: 200 }} />
        )}
        <Button title="Sign Up!" color="blue" onPress={this.handleSubmit} />
      </View>
    );
  }

  _pickImage = async () => {
    let result = await ImagePicker.launchImageLibraryAsync({
      allowsEditing: true,
      aspect: [4, 3],
    });

    console.log(result);

    if (!result.cancelled) {
      this.setState({ image: result.uri });
    }
  };
}

const styles = StyleSheet.create({
  container0: {
    justifyContent: 'center',
    color: 'blue',
    marginTop: 0,
    padding: 100,
    backgroundColor: '#6495ed',
  },
  /*container1: {
    justifyContent: 'center',
    marginTop: 0,
    padding: 100,
    backgroundColor: 'black',
  },*/
});
