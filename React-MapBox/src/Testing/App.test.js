import React from 'react';
import { findAllByRole, getAllByAltText, getByRole, render, screen } from '@testing-library/react';
import Map from '../App';

test('renders marker at Armstrong', () => {
  render(<Map />);
  // const markerTest = screen.getByRole('Marker');
  // expect(markerTest).toHaveAttribute("longitude", 84.73332)
  // expect(markerTest).toHaveAttribute("latitude", 39.507726)
});
