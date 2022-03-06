import { render, screen } from '@testing-library/react';
import Map from './App';

test('renders learn react link', () => {
  render(<Map />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
