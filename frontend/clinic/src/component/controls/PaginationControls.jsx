import './pagination-controls.css'

export default function PaginationControls({ previousPage, nextPage }) {
    return (
        <div className='controls'>
            <button
                className='previous'
                type='button'
                onClick={() => previousPage()}
            >
                Previous page</button>
            <button
                className='next'
                type='button'
                onClick={() => nextPage()}
            >
                Next page</button>
        </div>
    );
}