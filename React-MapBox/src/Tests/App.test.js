import React from 'react';
import { findAllByRole, getAllByAltText, getByRole, render, screen } from '@testing-library/react';
<<<<<<< HEAD:React-MapBox/src/Testing/App.test.js
import Map from '../App';
=======
import Map from '../Components/App';
>>>>>>> Nico-dev:wheelshare-web/src/Tests/App.test.js

test('renders marker at Armstrong', () => {
  render(<Map />);
  // const markerTest = screen.getByRole('Marker');
  // expect(markerTest).toHaveAttribute("longitude", 84.73332)
  // expect(markerTest).toHaveAttribute("latitude", 39.507726)
});
